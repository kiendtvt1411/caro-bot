package android.dolsoft;

import java.util.Random;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;

public class CaroMain extends Activity {
	
	private  MyView myChess;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myChess = (MyView)findViewById(R.id.myView1);
            
    }
    
 
    
}