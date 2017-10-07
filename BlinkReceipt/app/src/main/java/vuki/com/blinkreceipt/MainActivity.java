package vuki.com.blinkreceipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.microblink.activity.ScanCard;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkocr.BlinkOCRRecognizerSettings;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;

public class MainActivity extends AppCompatActivity {

    private static final String LICENCE_KEY = "V6D2M7PD-47E4BYLK-G3666S73-D2R7SF2C-3SFPWHYS-2IZUCHYS-2IZUDP7F-RXB4FNVU";
    private static final String LICENCE_ID_KEY = "C3S6Y75H-YWVZLOKQ-QOUPOQQT-Q7FOTLQV-P6BF2CZT-IEPRFURT-IEPRE4VC-VU7XBD4S";
    private static final int MY_REQUEST_CODE = 41;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // Intent for ScanCard Activity
        Intent intent = new Intent( this, ScanCard.class );
        intent.putExtra( ScanCard.EXTRAS_LICENSE_KEY, LICENCE_ID_KEY );

        RecognitionSettings settings = new RecognitionSettings();

        // setup array of recognition settings (described in chapter "Recognition
        // settings and results")
        settings.setRecognizerSettingsArray( setupSettingsArray() );
        intent.putExtra( ScanCard.EXTRAS_RECOGNITION_SETTINGS, settings );

        // Starting Activity
        startActivityForResult( intent, MY_REQUEST_CODE );
    }

    private RecognizerSettings[] setupSettingsArray() {

        RecognizerSettings recognizerSetting = new BlinkOCRRecognizerSettings();
        recognizerSetting.requiresAutofocus();
        recognizerSetting.setEnabled( true );

        RecognizerSettings[] recognizerSettingses = new RecognizerSettings[1];
        recognizerSettingses[0] = recognizerSetting;
        return recognizerSettingses;
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        //super.onActivityResult( requestCode, resultCode, data );
        if( requestCode == MY_REQUEST_CODE ) {
            if( resultCode == ScanCard.RESULT_OK && data != null ) {

                // perform processing of the data here

                // for example, obtain parcelable recognition result
                Bundle extras = data.getExtras();
                RecognitionResults result = data.getParcelableExtra( ScanCard.EXTRAS_RECOGNITION_RESULTS );

                // get array of recognition results
                BaseRecognitionResult[] resultArray = result.getRecognitionResults();
                // Each element in resultArray inherits BaseRecognitionResult class and
                // represents the scan result of one of activated recognizers that have
                // been set up. More information about this can be found in
                // "Recognition settings and results" chapter

                // Or, you can pass the intent to another activity
                //data.setComponent( new ComponentName( this, ResultActivity.class ) );
                //startActivity( data );
                Toast.makeText( this, data.getData().toString(), Toast.LENGTH_SHORT ).show();
            } else {
                Toast.makeText( this, "data is null", Toast.LENGTH_SHORT ).show();
            }
        }
    }
}
