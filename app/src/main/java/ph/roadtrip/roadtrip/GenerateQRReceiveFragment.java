package ph.roadtrip.roadtrip;

import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class GenerateQRReceiveFragment extends Fragment {
    LocationManager locationManager;
    String provider;

    private int bookingID;
    private SessionHandler session;
    private int userID;
    EditText text;

    ImageView image;
    String text2Qr = "Hello";
    private String url;
    private String currentLat;
    private String currentLong;
    private TextView tv_longitude, tv_latitude, tvBookingID, tvUserID;
    private Double amount;
    private String data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate_qr, container, false);

        //Get UserID
        session = new SessionHandler(getActivity().getApplicationContext());
        User user = session.getUserDetails();
        userID = user.getUserID();

        image = view.findViewById(R.id.image);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(String.valueOf(userID), BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        }
        catch (WriterException e){
            e.printStackTrace();
        }

        return view;
    }
}
