package com.organize4event.organize.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.CircleTransform;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.commons.Mask;
import com.organize4event.organize.commons.PreferencesManager;
import com.organize4event.organize.commons.validations.CpfCnpj;
import com.organize4event.organize.commons.validations.IsDate;
import com.organize4event.organize.controlers.FirstAccessControler;
import com.organize4event.organize.controlers.PrivacyControler;
import com.organize4event.organize.controlers.SettingsControler;
import com.organize4event.organize.controlers.TermUseControler;
import com.organize4event.organize.controlers.TokenControler;
import com.organize4event.organize.controlers.UserControler;
import com.organize4event.organize.enuns.DialogTypeEnum;
import com.organize4event.organize.enuns.PrivacyEnum;
import com.organize4event.organize.enuns.UserTypeEnum;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.listeners.CustomDialogListener;
import com.organize4event.organize.listeners.ToolbarListener;
import com.organize4event.organize.models.AccessPlatform;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.LoginType;
import com.organize4event.organize.models.Privacy;
import com.organize4event.organize.models.Setting;
import com.organize4event.organize.models.Token;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserSetting;
import com.organize4event.organize.models.UserTerm;
import com.organize4event.organize.models.UserType;
import com.organize4event.organize.utils.DataUtils;
import com.organize4event.organize.utils.MessageUtils;
import com.organize4event.organize.utils.ValidateUtils;
import com.organize4event.organize.utils.ViewUtils;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class UserRegisterActivity extends BaseActivity implements Validator.ValidationListener {

    Validator validator;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat format = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.contentImage)
    RelativeLayout contentImage;
    @Bind(R.id.imgProfile)
    ImageView imgProfile;
    @Bind(R.id.rgpListGender)
    RadioGroup rgpListGender;
    @Order(1)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @Bind(R.id.txtFullName)
    EditText txtFullName;
    @Order(2)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @CpfCnpj(sequence = 2, messageResId = R.string.validate_cpf)
    @Bind(R.id.txtCpf)
    EditText txtCpf;
    @Order(3)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @Email(sequence = 2, messageResId = R.string.validate_mail)
    @Bind(R.id.txtMail)
    EditText txtMail;
    @Order(4)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @IsDate(sequence = 2, messageResId = R.string.validate_date)
    @Bind(R.id.txtBirthDate)
    EditText txtBirthDate;
    @Order(5)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @Password(min = 6, sequence = 2, scheme = Password.Scheme.ALPHA_NUMERIC, messageResId = R.string.validate_password)
    @Bind(R.id.txtPassword)
    EditText txtPassword;
    @Order(6)
    @NotEmpty(trim = true, sequence = 1, messageResId = R.string.validate_required_field)
    @ConfirmPassword(sequence = 2, messageResId = R.string.validate_password_confirm)
    @Bind(R.id.txtPasswordConfirm)
    EditText txtPasswordConfirm;
    private Context context;
    private String message = "";
    private String title = "";
    private Date birthDate;
    private int term_accept = 0;
    private int checking = 1;
    private File file;
    private Uri uri;
    private User user;
    private FirstAccess firstAccess;
    private UserTerm userTerm;
    private UserType userType;
    private ArrayList<Privacy> privacies = new ArrayList<>();
    private ArrayList<Setting> settings = new ArrayList<>();
    private ArrayList<UserSetting> userSettings = new ArrayList<>();
    private UserSetting userSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ButterKnife.bind(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "USER REGISTER");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);


        context = UserRegisterActivity.this;
        firstAccess = Parcels.unwrap(getIntent().getExtras().getParcelable("firstAccess"));
        user = firstAccess.getUser();
        userTerm = user.getUser_term();


        configureToolbar(context, toolbar, context.getString(R.string.label_register_user), context.getResources().getDrawable(R.drawable.ic_arrow_back), true, new ToolbarListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        Validator.registerAnnotation(CpfCnpj.class);
        Validator.registerAnnotation(IsDate.class);
        validator = new Validator(context);
        validator.setValidationListener(this);


        txtCpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, txtCpf));
        txtBirthDate.addTextChangedListener(Mask.insert(Mask.DATE_MASK, txtBirthDate));

        selectGender();
    }

    protected void selectGender() {
        rgpListGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.btnGenderFem:
                        user.setGender("F");
                        break;
                    case R.id.btnGenderMasc:
                        user.setGender("M");
                        break;
                }
            }
        });
    }

    protected void getUserType() {
        showLoading();
        final int code_user_type = UserTypeEnum.DEFAULT.getValue();
        new UserControler(context).getUserType(firstAccess.getLocale(), code_user_type, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    userType = (UserType) object;
                    getPrivacy();
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void getPrivacy() {
        new PrivacyControler(context).getPrivacy(firstAccess.getLocale(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    privacies = (ArrayList<Privacy>) object;
                    if (privacies.size() > 0) {
                        for (Privacy privacy : privacies) {
                            if (privacy.getCode_enum() == PrivacyEnum.NO_ONE.getValue()) {
                                user.setPrivacy(privacy);
                                prepareUser();
                            }
                        }
                    }
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void prepareUser() {
        try {
            birthDate = format.parse(txtBirthDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        user.setUser_type(userType);
        user.setFull_name(txtFullName.getText().toString());
        user.setMail(txtMail.getText().toString());
        user.setPassword(txtPassword.getText().toString());
        user.setCpf(txtCpf.getText().toString());
        user.setBirth_date(birthDate);

        saveUser();
    }

    protected void saveUser() {
        new UserControler(context).saveUser(user, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    user = (User) object;
                    if (file != null) {
                        uploadPicture();
                    } else {
                        if (firstAccess.getId() == 0) {
                            saveFirstAccess();
                        } else {
                            updateFirstAccess();
                        }
                    }
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void saveFirstAccess() {
        firstAccess.setUser(user);
        new FirstAccessControler(context).saveFirstAccess(firstAccess, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    saveToken();
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void updateFirstAccess() {
        firstAccess.setUser(user);
        new FirstAccessControler(context).updateUserFirstAccess(firstAccess, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    saveToken();
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void saveToken() {
        final Token token = new Token();
        LoginType loginType = new LoginType();
        loginType.setId(1);
        AccessPlatform accessPlatform = new AccessPlatform();
        accessPlatform.setId(1);
        token.setLogin_type(loginType);
        token.setAccess_platform(accessPlatform);
        token.setAccess_date(new Date());
        token.setKeep_logged(false);
        new TokenControler(context).saveToken(token, user.getId(), 0, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    Token token = (Token) object;
                    user.setToken(token);
                    DataUtils.insertNotification(context, user.getId(), context.getString(R.string.notification_register_brief_description), context.getString(R.string.notification_register_description), new Date());
                    saveUserTerm();
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void saveUserTerm() {
        userTerm.setUser(user.getId());

        if (userTerm.isTerm_accept()) {
            term_accept = 1;
        }

        new TermUseControler(context).saveUserTerm(userTerm, term_accept, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    userTerm = (UserTerm) object;
                    user.setUser_term(userTerm);
                    getSettings();
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void getSettings() {
        new SettingsControler(context).getSettings(firstAccess.getLocale(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    settings = (ArrayList<Setting>) object;
                    if (settings.size() > 0) {
                        for (Setting setting : settings) {
                            saveUserSetting(setting);
                        }
                    }
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    public void saveUserSetting(final Setting setting) {

        userSetting = new UserSetting();
        userSetting.setUser(user.getId());
        userSetting.setSetting(setting);
        userSetting.setChecking(setting.isCheck_default());

        new SettingsControler(context).saveUserSettings(userSetting, checking, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (object != null) {
                    userSetting = (UserSetting) object;
                    userSettings.add(userSetting);

                    if (userSettings.size() == settings.size()) {
                        starLoginActivity();
                    }
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    protected void starLoginActivity() {
        ValidateUtils.sendValidateMail(context, user.getMail());
        MessageUtils.showDialogMessage(context, DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), context.getString(R.string.message_send_validate_mail), new CustomDialogListener() {
            @Override
            public void positiveOnClick(MaterialDialog dialog) {
                firstAccess.setUser(user);
                PreferencesManager.saveFirstAccess(firstAccess);
                AppApplication.setFirstAccess(firstAccess);

                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("firstAccess", Parcels.wrap(FirstAccess.class, firstAccess));
                startActivity(intent);
                finish();
            }

            @Override
            public void negativeOnClick(MaterialDialog dialog) {

            }
        });
    }

    @OnFocusChange({R.id.txtCpf, R.id.txtBirthDate, R.id.txtPassword, R.id.txtPasswordConfirm})
    public void actionOnFocusChange(View view) {
        title = context.getString(R.string.app_name);
        switch (view.getId()) {
            case R.id.txtCpf:
                message = context.getString(R.string.message_info_cpf);
                ViewUtils.hideOrShowInfoIcon(context, title, message, txtCpf);
                break;
            case R.id.txtBirthDate:
                message = context.getString(R.string.message_info_birth_date);
                ViewUtils.hideOrShowInfoIcon(context, title, message, txtBirthDate);
                break;
            case R.id.txtPassword:
                message = context.getString(R.string.message_info_password);
                ViewUtils.hideOrShowInfoIcon(context, title, message, txtPassword);
                break;
            case R.id.txtPasswordConfirm:
                message = context.getString(R.string.message_info_password);
                ViewUtils.hideOrShowInfoIcon(context, title, message, txtPasswordConfirm);
                break;
        }
    }

    @OnClick(R.id.imgAccept)
    public void actionUserRegister() {
        validator.validate();
    }

    @OnClick(R.id.contentImage)
    public void actionUploadImage() {
        MessageUtils.showDialogMessage(context, DialogTypeEnum.CAMARA_AND_GALERY, context.getString(R.string.label_upload_picture_title), context.getString(R.string.label_upload_picture), new CustomDialogListener() {
            @Override
            public void positiveOnClick(MaterialDialog dialog) {
                dialog.dismiss();
                onPickFromGaleryClicked();
            }

            @Override
            public void negativeOnClick(MaterialDialog dialog) {
                dialog.dismiss();
                onTakePhotoClicked();
            }
        });
    }

    public void uploadPicture() {
        File photo = resizeImage(file);
        new UserControler(context).uploadProfilePicture(user, photo, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                if (firstAccess.getId() == 0) {
                    saveFirstAccess();
                } else {
                    updateFirstAccess();
                }
            }

            @Override
            public void fail(Error error) {
                showErrorMessage(context, error);
            }
        });
    }

    public File resizeImage(File file) {
        Bitmap bFile = BitmapFactory.decodeFile(file.getPath());
        int width = bFile.getWidth();
        int height = bFile.getHeight();

        float newWidth = ((float) 300) / width;
        float newHeigth = ((float) 300) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(newWidth, newHeigth);
        Bitmap resizedBitmap = Bitmap.createBitmap(bFile, 0, 0, width, height, matrix, false);

        File resizedFile = new File(context.getCacheDir(), "photoProfile");
        try {
            resizedFile.createNewFile();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bitmapdata = byteArrayOutputStream.toByteArray();

            FileOutputStream fileOutputStream = new FileOutputStream(resizedFile);
            fileOutputStream.write(bitmapdata);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resizedFile;
    }

    public void onPickFromGaleryClicked() {
        EasyImage.openGallery(this, 0);
    }

    public void onTakePhotoClicked() {
        int permissionCheckWriteExternal = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckCamera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (permissionCheckWriteExternal == PackageManager.PERMISSION_GRANTED && permissionCheckCamera == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openCamera(this, 0);
        } else {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            Nammu.askForPermission((Activity) context, permissions, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    EasyImage.openCamera((Activity) context, 0);
                }

                @Override
                public void permissionRefused() {
                    MessageUtils.showDialogMessage(context, DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), context.getString(R.string.info_permission_camara), new CustomDialogListener() {
                        @Override
                        public void positiveOnClick(MaterialDialog dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void negativeOnClick(MaterialDialog dialog) {

                        }
                    });
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void onPhotoReturned(File profileFile) {
        file = profileFile;
        Glide.with(context).load(profileFile).centerCrop().transform(new CircleTransform(context)).into(imgProfile);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onValidationSucceeded() {
        getUserType();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidateUtils.validateError(context, errors);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, (Activity) context, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                MessageUtils.showDialogMessage(context, DialogTypeEnum.JUSTPOSITIVE, context.getString(R.string.app_name), context.getString(R.string.error_photo_loading), new CustomDialogListener() {
                    @Override
                    public void positiveOnClick(MaterialDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void negativeOnClick(MaterialDialog dialog) {

                    }
                });
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                file = imageFile;
                onPhotoReturned(imageFile);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(context);
                    if (photoFile != null) {
                        photoFile.delete();
                    }
                }
            }
        });
    }
}
