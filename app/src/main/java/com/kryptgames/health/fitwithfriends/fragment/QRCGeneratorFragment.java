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

import java.util.Calendar;
import java.util.Random;

public class QRCGeneratorFragment extends Fragment {

    String a;
    int b,c,d,e,f,year,month,day;
    Random random;
    QRCodeWriter qrCodeWriter;
    TextView rewardTitle,placeDescription,offerDescription,locationDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.avail_reward_qrcode,container,false);
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
        offerDescription.setText("Get 1 free mug of beer ar Zero Forty Brewery,Jubilee Hills.\nValid on all craft beer on all days\nMaximum of 1copon per person per day.");
        locationDescription.setText("040 Road Number 32\nJubilee Hills\nHyderabad 500081");

        qrCodeWriter=new QRCodeWriter();
        random=new Random();
        b=random.nextInt(10);
        c=random.nextInt(10);
        d=random.nextInt(10);
        e=random.nextInt(10);
        f=random.nextInt(10);
        a="FIT-";
        Calendar calendar=Calendar.getInstance();
        int z =(calendar.get(Calendar.YEAR)/100);
        year=(z*100)-(calendar.get(Calendar.YEAR));
        month = (calendar.get(Calendar.MONTH))+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(a+"0"+month+day+year+b+c+d+e+f, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) getView().findViewById(R.id.fwf_imageview_qrcode)).setImageBitmap(bmp);

        } catch (WriterException exception) {
            exception.printStackTrace();
        }
        super.onActivityCreated(savedInstanceState);
    }
}
