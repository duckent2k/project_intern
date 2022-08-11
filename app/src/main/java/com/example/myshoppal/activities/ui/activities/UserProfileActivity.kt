package com.example.myshoppal.activities.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myshoppal.firestore.FireStoreClass
import com.example.myshoppal.models.User
import com.example.myshoppal.utils.Constants
import com.example.myshoppal.utils.GlideLoader
import com.myshoppal.R
import com.myshoppal.databinding.ActivityUserProfileBinding

class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mUserDetails: User
    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageURL: String = ""

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            mUserDetails = intent.getSerializableExtra(Constants.EXTRA_USER_DETAILS) as User

            binding.etFirstName.setText(mUserDetails.firstname)
            binding.etLastName.setText(mUserDetails.lastname)
            binding.etEmail.isEnabled = false
            binding.etEmail.setText(mUserDetails.email)

            if (mUserDetails.profileCompleted == 0) {
                binding.tvTitle.text = resources.getString(R.string.title_complete_profile)
                binding.etFirstName.isEnabled = false
                binding.etLastName.isEnabled = false

            }else {
                setUpActionBar()
                binding.tvTitle.text = resources.getString(R.string.title_edit_profile)
                GlideLoader(this@UserProfileActivity).loadUserPicture(mUserDetails.image ?: "", binding.ivUserPhoto)

                binding.etEmail.isEnabled = false
                binding.etEmail.setText(mUserDetails.email)

                binding.etMobileNumber.setText(mUserDetails.mobile)

                if (mUserDetails.gender == Constants.MALE) {
                    binding.rbMale.isChecked = true
                } else{
                    binding.rbFemale.isChecked = true
                }

            }

        }




        binding.ivUserPhoto.setOnClickListener(this@UserProfileActivity)

        binding.btnSave.setOnClickListener(this@UserProfileActivity)


    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbarUserProfileActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_color_black_24dp)
        binding.toolbarUserProfileActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }



//        binding.btnListFriends.setOnClickListener {
//            val intent = Intent(this@UserProfileActivity, ListFriendsActivity::class.java)
//            startActivity(intent)
//        }

//        val sharedPref =
//            this@UserProfileActivity.getSharedPreferences(
//                Constants.MYSHOPPAL_PREFERENCES,
//                Context.MODE_PRIVATE
//            )

//        binding.etFirstName.setText("FIRST NAME: " + sharedPref.getString(Constants.FIRST_NAME, ""))
//        binding.etLastName.setText("LAST NAME: " + sharedPref.getString(Constants.LAST_NAME, ""))
//        binding.etEmail.setText("EMAIL: " + sharedPref.getString(Constants.EMAIL, ""))
//        binding.etMobileNumber.setText("MOBILE: " + sharedPref.getString(Constants.MOBILE, ""))

//        binding.profile = User(
//            firstname = sharedPref.getString(Constants.FIRST_NAME, "") ?: "",
//            lastname = sharedPref.getString(Constants.LAST_NAME, "") ?: "",
//            email = sharedPref.getString(Constants.EMAIL, "") ?: "",
//            mobile = sharedPref.getString(Constants.MOBILE, "") ?: "",
//            gender = sharedPref.getString(Constants.GENDER, "") ?: ""
//        )


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.iv_user_photo -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this@UserProfileActivity)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_save -> {
                    if (validateUserProfileDetails()) {
                        showProgressDialog(resources.getString(R.string.please_wait))

                        if (mSelectedImageFileUri != null) {
                            FireStoreClass().uploadImageToCloudStorage(
                                this@UserProfileActivity,
                                mSelectedImageFileUri,
                                Constants.USER_PROFILE_IMAGE
                            )
                        } else {
                            updateUserProfileDetails()
                        }
                    }
                }
            }
        }
    }


    private fun updateUserProfileDetails() {

        val userHashMap = HashMap<String, Any>()

        val firstName = binding.etFirstName.text.toString().trim{it <= ' '}
        if (firstName != mUserDetails.firstname) {
            userHashMap[Constants.FIRST_NAME ] = firstName
        }

        val lastName = binding.etLastName.text.toString().trim{it <= ' '}
        if (lastName != mUserDetails.lastname) {
            userHashMap[Constants.LAST_NAME ] = lastName
        }

        val mobileNumber =
            binding.etMobileNumber.text.toString().trim { it <= ' ' }

        val gender = if (binding.rbMale.isChecked) {
            Constants.MALE
        } else {
            Constants.FEMALE
        }

        if (mUserProfileImageURL.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = mUserProfileImageURL
        }

        if (mobileNumber.isNotEmpty() && mobileNumber != mUserDetails.mobile) {
            userHashMap[Constants.MOBILE] = mobileNumber
        }

        if (gender.isNotEmpty() && gender != mUserDetails.gender){
            userHashMap[Constants.GENDER] = gender
        }


        if (mUserDetails.profileCompleted == 0) {
            userHashMap[Constants.COMPLETE_PROFILE] = 1
        }

//        showProgressDialog(resources.getString(R.string.please_wait))

        FireStoreClass().updateUserProfileData(this, userHashMap)

    }

    fun userProfileUpdateSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@UserProfileActivity,
            resources.getString(R.string.msg_profile_update_success),
            Toast.LENGTH_SHORT
        ).show()

        startActivity(Intent(this@UserProfileActivity, DashboardActivity::class.java))
        finish()
    }


    private fun validateUserProfileDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etMobileNumber.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }
            else -> {
                true
            }
        }
    }

    fun imageUploadSuccess(imageURL: String) {
        mUserProfileImageURL = imageURL
        updateUserProfileDetails()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
            data?.let {
                mSelectedImageFileUri = it.data
                GlideLoader(this).loadUserPicture(mSelectedImageFileUri, binding.ivUserPhoto)
            }
        }

    }
}

