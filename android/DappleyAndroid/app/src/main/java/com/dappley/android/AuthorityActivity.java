package com.dappley.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dappley.android.listener.BtnBackListener;
import com.dappley.android.util.VersionUtil;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthorityActivity extends AppCompatActivity {
    private static String PLACE_HOLDER = "%s";
    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.txt_title)
    TextView tvTitle;
    @BindView(R.id.txt_content)
    TextView tvContent;
    @BindView(R.id.rg_phone)
    RadioGroup rgPhone;

    @BindString(R.string.mobile_note_huawei)
    String mobileNoteHuawei;
    @BindString(R.string.mobile_note_xiaomi)
    String mobileNoteXiaomi;
    @BindString(R.string.mobile_note_samsung)
    String mobileNoteSamsung;
    @BindString(R.string.mobile_note_lenovo)
    String mobileNoteLenovo;
    @BindString(R.string.mobile_note_oppo)
    String mobileNoteOppo;
    @BindString(R.string.mobile_note_vivo)
    String mobileNoteVivo;
    @BindString(R.string.mobile_note_zte)
    String mobileNoteZte;
    @BindString(R.string.mobile_note_meizu)
    String mobileNoteMeizu;
    @BindString(R.string.mobile_note_leshi)
    String mobileNoteLeshi;
    @BindString(R.string.mobile_note_jinli)
    String mobileNoteJinli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority);

        ButterKnife.bind(this);

        initView();

        initData();
    }

    private void initView() {
        tvTitle.setText(R.string.title_authority);
        btnBack.setOnClickListener(new BtnBackListener(this));
    }

    private void initData() {
        rgPhone.check(R.id.rb_huawei);
        switchPhone(R.id.rb_huawei);
    }

    @OnClick({R.id.rb_huawei, R.id.rb_xiaomi, R.id.rb_samsung, R.id.rb_lenovo, R.id.rb_oppo, R.id.rb_vivo, R.id.rb_zte, R.id.rb_meizu
            , R.id.rb_leshi, R.id.rb_jinli})
    void mobileClick(RadioButton radioButton) {
        switchPhone(radioButton.getId());
    }

    private void switchPhone(int id) {
        String appName = VersionUtil.getAppName(this);
        switch (id) {
            case R.id.rb_huawei:
                tvContent.setText(mobileNoteHuawei.replace(PLACE_HOLDER, appName));
                break;
            case R.id.rb_xiaomi:
                tvContent.setText(mobileNoteXiaomi.replace(PLACE_HOLDER, appName));
                break;
            case R.id.rb_samsung:
                tvContent.setText(mobileNoteSamsung.replace(PLACE_HOLDER, appName));
                break;
            case R.id.rb_lenovo:
                tvContent.setText(mobileNoteLenovo.replace(PLACE_HOLDER, appName));
                break;
            case R.id.rb_oppo:
                tvContent.setText(mobileNoteOppo.replace(PLACE_HOLDER, appName));
                break;
            case R.id.rb_vivo:
                tvContent.setText(mobileNoteVivo.replace(PLACE_HOLDER, appName));
                break;
            case R.id.rb_zte:
                tvContent.setText(mobileNoteZte.replace(PLACE_HOLDER, appName));
                break;
            case R.id.rb_meizu:
                tvContent.setText(mobileNoteMeizu.replace(PLACE_HOLDER, appName));
                break;
            case R.id.rb_leshi:
                tvContent.setText(mobileNoteLeshi.replace(PLACE_HOLDER, appName));
                break;
            case R.id.rb_jinli:
                tvContent.setText(mobileNoteJinli.replace(PLACE_HOLDER, appName));
                break;
            default:
                break;
        }
    }
}
