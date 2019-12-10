package ph.roadtrip.roadtrip;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;


public class QRFragment extends Fragment {
    private int userID;

    private SessionHandler session;

    private ImageView imgSend, imgReceive;

    private Button btnConfirm, btnCancel;
    private EditText etAmount;
    private Double amount;

    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        imgSend = view.findViewById(R.id.imgSend);
        imgReceive = view.findViewById(R.id.imgReceive);

        //Get UserID
        session = new SessionHandler(getActivity().getApplicationContext());
        User user = session.getUserDetails();
        userID = user.getUserID();

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLoginDialog();
            }
        });

        imgReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Show QR Scanner
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new QRReceiveFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void callLoginDialog()
    {
        final Dialog myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.prompt_amount);
        myDialog.setCancelable(false);

        etAmount = (EditText) myDialog.findViewById(R.id.etAmount);
        btnConfirm =  (Button) myDialog.findViewById(R.id.btnConfirm);
        btnCancel =  (Button) myDialog.findViewById(R.id.btnCancel);
        myDialog.show();

        btnConfirm.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String amount2 = etAmount.getText().toString();
                amount = Double.parseDouble(amount2);
                myDialog.hide();

                GenerateQRSendFragment ldf = new GenerateQRSendFragment();
                Bundle args = new Bundle();
                args.putDouble("amount", amount);
                ldf.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, ldf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDialog.hide();
            }
        });

    }
}
