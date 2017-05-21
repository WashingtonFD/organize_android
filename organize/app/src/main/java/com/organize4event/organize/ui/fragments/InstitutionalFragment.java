package com.organize4event.organize.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.organize4event.organize.R;
import com.organize4event.organize.commons.AppApplication;
import com.organize4event.organize.controlers.InstitutionalControler;
import com.organize4event.organize.controlers.PlanControler;
import com.organize4event.organize.enuns.ContactTypeEnum;
import com.organize4event.organize.enuns.DialogTypeEnum;
import com.organize4event.organize.enuns.PlanEnum;
import com.organize4event.organize.listeners.ControlResponseListener;
import com.organize4event.organize.listeners.CustomDialogListener;
import com.organize4event.organize.listeners.SectionListener;
import com.organize4event.organize.models.Contact;
import com.organize4event.organize.models.FirstAccess;
import com.organize4event.organize.models.Institutional;
import com.organize4event.organize.models.Plan;
import com.organize4event.organize.ui.activities.PlanDetailActivity;
import com.organize4event.organize.ui.sections.ContactSection;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class InstitutionalFragment extends BaseFragment {
    @Bind(R.id.contentFilter)
    RelativeLayout contentFilter;
    @Bind(R.id.txtDescription)
    TextView txtDescription;
    @Bind(R.id.txtMission)
    TextView txtMission;
    @Bind(R.id.txtVision)
    TextView txtVision;
    @Bind(R.id.txtValues)
    TextView txtValues;
    @Bind(R.id.txtFree)
    TextView txtFree;
    @Bind(R.id.txtBasic)
    TextView txtBasic;
    @Bind(R.id.txtPremium)
    TextView txtPremium;
    @Bind(R.id.txtPlanFree)
    TextView txtPlanFree;
    @Bind(R.id.txtPlanBasic)
    TextView txtPlanBasic;
    @Bind(R.id.txtPlanPremium)
    TextView txtPlanPremium;
    @Bind(R.id.listContacts)
    RecyclerView listContacts;
    private Context context;
    private FirstAccess firstAccess;
    private ArrayList<Plan> plans;
    private ArrayList<Contact> contacts;
    private Institutional institutional;
    private SectionedRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_institutional, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "INSTITUTIONAL");
        FirebaseAnalytics.getInstance(getActivity()).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);


        context = getActivity();
        firstAccess = AppApplication.getFirstAccess();

        getInstitutional();
        return view;
    }

    public void getInstitutional() {
        showLoading();
        new InstitutionalControler(context).getInstitutional(firstAccess.getLocale(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                institutional = (Institutional) object;
                txtDescription.setText(institutional.getDescription());
                txtMission.setText(institutional.getMission());
                txtVision.setText(institutional.getVision());
                txtValues.setText(institutional.getValues());

                getPlans();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    public void getPlans() {
        new PlanControler(context).getPlan(firstAccess.getLocale(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                plans = (ArrayList<Plan>) object;
                for (Plan plan : plans) {
                    if (plan.getCode_enum() == PlanEnum.FREE.getValue()) {
                        txtFree.setText(plan.getName());
                        txtPlanFree.setText(plan.getDescription());
                    } else if (plan.getCode_enum() == PlanEnum.BASIC.getValue()) {
                        txtBasic.setText(plan.getName());
                        txtPlanBasic.setText(plan.getDescription());
                    } else {
                        txtPremium.setText(plan.getName());
                        txtPlanPremium.setText(plan.getDescription());
                    }
                }

                getContacts();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    public void getContacts() {
        new InstitutionalControler(context).getContact(firstAccess.getLocale(), new ControlResponseListener() {
            @Override
            public void success(Object object) {
                contacts = (ArrayList<Contact>) object;
                adapter = new SectionedRecyclerViewAdapter();
                TreeMap<String, ContactSection> sections = new TreeMap<String, ContactSection>();
                for (Contact contact : contacts) {
                    ContactSection contactSection;
                    String key = contact.getContact_type().getName();
                    if (sections.containsKey(key)) {
                        contactSection = sections.get(key);
                    } else {
                        contactSection = new ContactSection(context, new SectionListener() {
                            @Override
                            public void onClick(Object object) {
                                Contact cont = (Contact) object;
                                startContact(cont);
                            }
                        });
                    }
                    if (contact.is_active()) {
                        contactSection.addItem(contact);
                        sections.put(key, contactSection);
                    }
                }

                for (Map.Entry<String, ContactSection> entry : sections.entrySet()) {
                    ContactSection value = entry.getValue();
                    adapter.addSection(value);
                }

                listContacts.setLayoutManager(new LinearLayoutManager(context));
                listContacts.setItemAnimator(new DefaultItemAnimator());
                listContacts.setAdapter(adapter);
                contentFilter.setVisibility(View.GONE);
                hideLoading();
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    public void getPlanId(int code_enum) {
        showLoading();
        new PlanControler(context).getPlanId(firstAccess.getLocale(), code_enum, new ControlResponseListener() {
            @Override
            public void success(Object object) {
                hideLoading();
                Plan plan = (Plan) object;
                Intent intent = new Intent(context, PlanDetailActivity.class);
                intent.putExtra("plan", Parcels.wrap(Plan.class, plan));
                startActivity(intent);
            }

            @Override
            public void fail(Error error) {
                returnErrorMessage(error, context);
            }
        });
    }

    public void startContact(Contact contact) {
        ContactTypeEnum contact_type = ContactTypeEnum.values()[contact.getContact_type().getCode_enum() - 1];
        switch (contact_type) {
            case EMAIL:
                sendEmail(contact);
                break;
            case CELLPHONE:
                callPhone(contact);
                break;
            case PHONE:
                callPhone(contact);
                break;
            case WHATSAPP:
                sendWhasappMessage(contact);
                break;
            case FACEBOOK:
                openPageFacebook(context);
                break;
        }
    }

    public void sendEmail(Contact contact) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{contact.getContact()});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.label_subject));
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, context.getString(R.string.label_send_mail)));
    }

    public void callPhone(final Contact contact) {
        showDialogMessage(DialogTypeEnum.POSITIVE_AND_NEGATIVE, context.getString(R.string.label_call), context.getString(R.string.message_call_phone), new CustomDialogListener() {
            @Override
            public void positiveOnClick(MaterialDialog dialog) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getContact()));
                startActivity(intent);
                dialog.dismiss();
            }

            @Override
            public void negativeOnClick(MaterialDialog dialog) {
                dialog.dismiss();
            }
        });
    }

    public void sendWhasappMessage(Contact contact) {
        Uri uri = Uri.parse("smsto:" + contact.getContact());
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
        sendIntent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(sendIntent, ""));
        //TODO: MELHORAR ISTO - WHATSAPP
    }

    public void openPageFacebook(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1881281328782009")));
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/organize4event")));
        }
    }

    @OnClick({R.id.txtFree, R.id.txtBasic, R.id.txtPremium, R.id.txtSite})
    public void actionStartPlanDetail(View view) {
        Plan plan = plans.get(0);
        switch (view.getId()) {
            case R.id.txtFree:
                getPlanId(PlanEnum.FREE.getValue());
                break;
            case R.id.txtBasic:
                getPlanId(PlanEnum.BASIC.getValue());
                break;
            case R.id.txtPremium:
                getPlanId(PlanEnum.PREMIUM.getValue());
                break;
            case R.id.txtSite:
                Uri site = Uri.parse("http://organize4event.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, site);
                startActivity(intent);
                break;
        }
    }
}
