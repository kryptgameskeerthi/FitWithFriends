package com.kryptgames.health.fitwithfriends.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kryptgames.health.fitwithfriends.R;



public class QRCGeneratorFragment extends Fragment {

    private TextView rewardTitle,placeDescription,offerDescription,locationDescription;
    private Bitmap bmp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.avail_reward_qrcode,container,false);
        Bundle bundle=this.getArguments();
        bmp=qrcGenerator(bundle.getInt("missionId"));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        rewardTitle=getView().findViewById(R.id.fwf_textview_rewardtitle);
        rewardTitle.setText(this.getArguments().getString("title"));

        placeDescription=getView().findViewById(R.id.fwf_textview_placedescription);
        offerDescription=getView().findViewById(R.id.fwf_textview_offerdescription);
        locationDescription=getView().findViewById(R.id.fwf_textview_locationdescription);

        placeDescription.setText("Zero Forty has the finest beer in Hyderabad handcrafted using locally sourced,GM free,organic ingredients. The beer we serve is freshly crafted on location with extreme care to ensure the most consistent and flavourful tastes for our connoisseurs.");
        offerDescription.setText("Get 1 free mug of beer ar Zero Forty Brewery,Jubilee Hills.\nValid on all craft beer on all days\nMaximum of 1coupon per person per day.");
        locationDescription.setText("040 Road Number 32\nJubilee Hills\nHyderabad 500081");

        ((ImageView) getView().findViewById(R.id.fwf_imageview_qrcode)).setImageBitmap(bmp);

        super.onActivityCreated(savedInstanceState);
    }

    private Bitmap qrcGenerator(int missnId){
        int width=512,height=512;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Bitmap bmp1=Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode("FIT-"+missnId, BarcodeFormat.QR_CODE, width, height);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp1.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException exception) {
            exception.printStackTrace();
        }
        return bmp1;
    }
}
