package pfa.src;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MenuFragment extends Fragment {

    private static final String TAG = "MenuFragment";

    private OnFragmentInteractionListener mListener;

    private int mFrameProc = 0;


    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Button Simulation
        RadioButton tb_tritanopia_simulation = view.findViewById(R.id.tritanopia_simulation);
        RadioButton tb_protanopia_simulation = view.findViewById(R.id.protanopia_simulation);
        RadioButton tb_deuteranopia_simulation = view.findViewById(R.id.deuteranopia_simulation);

        //Button Correction
        RadioButton tb_tritanopia_correction = view.findViewById(R.id.tritanopia_correction);
        RadioButton tb_protanopia_correction = view.findViewById(R.id.protanopia_correction);
        RadioButton tb_deuteranopia_correction = view.findViewById(R.id.deuteranopia_correction);

        //Button to launch the camera
        Button b_launchCamera = view.findViewById(R.id.launchCamera);

        //Bouton to leave space between Simulation and Correction
        RadioButton hideButton  = view.findViewById(R.id.button_hide);
        hideButton.setVisibility(View.INVISIBLE);

        // Button Listener
        tb_tritanopia_simulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    mFrameProc = FrameProcFactory.TRITANOPIA;
                }
            }
        });

        tb_protanopia_simulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mFrameProc = FrameProcFactory.PROTANOPIA;
                }
            }
        });

        tb_deuteranopia_simulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mFrameProc = FrameProcFactory.DEUTERANOPIA;
                }
            }
        });

        tb_tritanopia_correction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mFrameProc = FrameProcFactory.CORRECT_TRITANOPIA;
                }
            }
        });

        tb_protanopia_correction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mFrameProc = FrameProcFactory.CORRECT_PROTANOPIA;
                }
            }
        });

        tb_deuteranopia_correction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mFrameProc = FrameProcFactory.CORRECT_DEUTERANOPIA;
                }
            }
        });

        b_launchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed(mFrameProc);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int frameProc) {
        if (mListener != null) {
            mListener.onFragmentInteraction(frameProc);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by the activity {@link MainActivity} that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity {@link CameraFragment}.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int frameProc);
    }
}
