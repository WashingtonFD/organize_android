package com.organize4event.organize.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.commons.CircleTransform;
import com.organize4event.organize.commons.Constants;
import com.organize4event.organize.commons.Mask;
import com.organize4event.organize.commons.validations.CpfCnpj;
import com.organize4event.organize.commons.validations.IsDate;
import com.organize4event.organize.models.User;
import com.organize4event.organize.ui.activities.HomeActivity;
import com.organize4event.organize.utils.MessageUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalDataFragment extends BaseFragment implements Validator.ValidationListener {
    Validator validator;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat format = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
    @Bind(R.id.contentImage)
    RelativeLayout contentImage;
    @Bind(R.id.imgProfile)
    ImageView imgProfile;
    @Bind(R.id.rgpListGender)
    RadioGroup rgpListGender;
    @Bind(R.id.btnGenderFem)
    RadioButton btnGenderFem;
    @Bind(R.id.btnGenderMasc)
    RadioButton btnGenderMasc;
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
    @IsDate(sequence = 2, messageResId = R.string.validate_date)
    @Bind(R.id.txtBirthDate)
    EditText txtBirthDate;
    @Order(4)
    @Bind(R.id.txtRgNumber)
    EditText txtRgNumber;
    @Order(5)
    @IsDate(sequence = 1, messageResId = R.string.validate_date)
    @Bind(R.id.txtRgDate)
    EditText txtRgDate;
    @Order(6)
    @Bind(R.id.txtRgOrgao)
    EditText txtRgOrgao;
    @Order(7)
    @Bind(R.id.txtRgUf)
    EditText txtRgUf;
    @Bind(R.id.contentResponsibleData)
    RelativeLayout contentResponsibleData;
    @Order(8)
    @Bind(R.id.txtResponsibleName)
    EditText txtResponsibleName;
    @Order(9)
    @Bind(R.id.txtResponsibleCpf)
    EditText txtResponsibleCpf;
    @Bind(R.id.btnUserDataSave)
    Button btnUserDataSave;
    private Context context;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_data, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "PERSONAL DATA");
        FirebaseAnalytics.getInstance(getActivity()).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);

        context = getActivity();
        user = AppApplication.getFirstAccess().getUser();

        ((HomeActivity) getActivity()).setEditMode(false);
        btnUserDataSave.setVisibility(View.GONE);
        disableFields();

        Validator.registerAnnotation(CpfCnpj.class);
        Validator.registerAnnotation(IsDate.class);
        validator = new Validator(context);
        validator.setValidationListener(this);

        txtCpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK, txtCpf));
        txtBirthDate.addTextChangedListener(Mask.insert(Mask.DATE_MASK, txtBirthDate));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    public void setData() {
        if (user.getProfile_picture() != null) {
            Glide.with(context).load(user.getProfile_picture()).centerCrop().transform(new CircleTransform(context)).into(imgProfile);
        }

        if (user.getFull_name() != null) {
            txtFullName.setText(user.getFull_name());
        }
        if (user.getCpf() != null) {
            txtCpf.setText(user.getCpf());
        }
        if (user.getBirth_date() != null) {
            txtBirthDate.setText(format.format(user.getBirth_date()));
        }
        if (user.getRg_number() != null) {
            txtRgNumber.setText(user.getRg_number());
        }
        if (user.getRg_emitter_uf() != null) {
            txtRgUf.setText(user.getRg_emitter_uf());
        }
        if (user.getRg_emitter_organ() != null) {
            txtRgOrgao.setText(user.getRg_emitter_organ());
        }
        if (user.getRg_emitter_date() != null) {
            txtRgDate.setText(format.format(user.getRg_emitter_date()));
        }
        if (user.getResponsible_name() != null) {
            txtResponsibleName.setText(user.getResponsible_name());
        }
        if (user.getResponsible_cpf() != null) {
            txtResponsibleCpf.setText(user.getResponsible_cpf());
        }

        if (user.getGender().equals("F")) {
            btnGenderFem.setChecked(true);
            btnGenderMasc.setChecked(false);
        } else {
            btnGenderFem.setChecked(false);
            btnGenderMasc.setChecked(true);
        }

        if ((new Date().getYear() - user.getBirth_date().getYear()) < 18) {
            contentResponsibleData.setVisibility(View.VISIBLE);
        } else {
            contentResponsibleData.setVisibility(View.GONE);
        }

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

    public void verifyModeEdit() {
        if (((HomeActivity) getActivity()).isEditMode()) {
            enableFields();
            MessageUtils.showToastMessage(context, "Modo edição habilitado");
        } else {
            disableFields();
        }
    }

    protected void enableFields() {
        txtFullName.setEnabled(true);
        txtCpf.setEnabled(true);
        txtBirthDate.setEnabled(true);
        txtRgNumber.setEnabled(true);
        txtRgUf.setEnabled(true);
        txtRgOrgao.setEnabled(true);
        txtRgDate.setEnabled(true);
        txtResponsibleName.setEnabled(true);
        txtResponsibleCpf.setEnabled(true);
        btnGenderFem.setEnabled(true);
        btnGenderMasc.setEnabled(true);
        contentImage.setClickable(true);
        btnUserDataSave.setVisibility(View.VISIBLE);
    }

    protected void disableFields() {
        txtFullName.setEnabled(false);
        txtCpf.setEnabled(false);
        txtBirthDate.setEnabled(false);
        txtRgNumber.setEnabled(false);
        txtRgUf.setEnabled(false);
        txtRgOrgao.setEnabled(false);
        txtRgDate.setEnabled(false);
        txtResponsibleName.setEnabled(false);
        txtResponsibleCpf.setEnabled(false);
        btnGenderFem.setEnabled(false);
        btnGenderMasc.setEnabled(false);
        contentImage.setClickable(false);
        btnUserDataSave.setVisibility(View.GONE);
    }

    @OnClick(R.id.contentImage)
    public void actionUploadImage() {
        MessageUtils.showToastMessage(context, "Upload Image");
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
