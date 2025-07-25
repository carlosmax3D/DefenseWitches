package facebook;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeTask;
import com.facebook.internal.NativeProtocol;
import com.facebook.model.GraphLocation;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.share.internal.ShareConstants;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.PickerFragment;
import com.facebook.widget.PlacePickerFragment;
import com.naef.jnlua.LuaState;
import java.util.List;

public class FacebookFragmentActivity extends FragmentActivity {
    private static final int CONTENT_VIEW_ID = 192875;
    public static final String FRAGMENT_EXTRAS = "fragment_extras";
    public static final String FRAGMENT_LISTENER = "fragment_listener";
    public static final String FRAGMENT_NAME = "fragment_name";
    private PickerFragment mFragment;
    /* access modifiers changed from: private */
    public int mListener;

    /* access modifiers changed from: private */
    public void pushFriendSelection(final List<GraphUser> list) {
        CoronaEnvironment.getCoronaActivity().getRuntimeTaskDispatcher().send(new CoronaRuntimeTask() {
            private void pushGraphUser(LuaState luaState, GraphUser graphUser, int i) {
                luaState.newTable(0, 4);
                FacebookFragmentActivity.this.pushStringIfNotNull(luaState, graphUser.getFirstName(), "firstName");
                FacebookFragmentActivity.this.pushStringIfNotNull(luaState, graphUser.getLastName(), "lastName");
                FacebookFragmentActivity.this.pushStringIfNotNull(luaState, graphUser.getName(), "fullName");
                FacebookFragmentActivity.this.pushStringIfNotNull(luaState, graphUser.getId(), ShareConstants.WEB_DIALOG_PARAM_ID);
                luaState.rawSet(-2, i);
            }

            public void executeUsing(CoronaRuntime coronaRuntime) {
                LuaState luaState;
                if (coronaRuntime != null && list != null && FacebookFragmentActivity.this.mListener > 0 && (luaState = coronaRuntime.getLuaState()) != null) {
                    CoronaLua.newEvent(luaState, NativeProtocol.AUDIENCE_FRIENDS);
                    luaState.newTable(0, list.size());
                    int i = 1;
                    for (GraphUser pushGraphUser : list) {
                        pushGraphUser(luaState, pushGraphUser, i);
                        i++;
                    }
                    luaState.setField(-2, "data");
                    try {
                        CoronaLua.dispatchEvent(luaState, FacebookFragmentActivity.this.mListener, 0);
                        CoronaLua.deleteRef(luaState, FacebookFragmentActivity.this.mListener);
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void pushPlaceSelection(final GraphPlace graphPlace) {
        CoronaEnvironment.getCoronaActivity().getRuntimeTaskDispatcher().send(new CoronaRuntimeTask() {
            public void executeUsing(CoronaRuntime coronaRuntime) {
                LuaState luaState;
                if (coronaRuntime != null && graphPlace != null && FacebookFragmentActivity.this.mListener > 0 && (luaState = coronaRuntime.getLuaState()) != null) {
                    CoronaLua.newEvent(luaState, "place");
                    luaState.newTable(0, 11);
                    FacebookFragmentActivity.this.pushStringIfNotNull(luaState, graphPlace.getCategory(), "category");
                    FacebookFragmentActivity.this.pushStringIfNotNull(luaState, graphPlace.getId(), ShareConstants.WEB_DIALOG_PARAM_ID);
                    FacebookFragmentActivity.this.pushStringIfNotNull(luaState, graphPlace.getName(), "name");
                    GraphLocation location = graphPlace.getLocation();
                    if (location != null) {
                        FacebookFragmentActivity.this.pushStringIfNotNull(luaState, location.getCity(), "city");
                        FacebookFragmentActivity.this.pushStringIfNotNull(luaState, location.getCountry(), "country");
                        FacebookFragmentActivity.this.pushStringIfNotNull(luaState, location.getState(), "state");
                        FacebookFragmentActivity.this.pushStringIfNotNull(luaState, location.getStreet(), "street");
                        FacebookFragmentActivity.this.pushStringIfNotNull(luaState, location.getZip(), "zip");
                        luaState.pushNumber(location.getLatitude());
                        luaState.setField(-2, "latitude");
                        luaState.pushNumber(location.getLongitude());
                        luaState.setField(-2, "longitude");
                    }
                    luaState.setField(-2, "data");
                    try {
                        CoronaLua.dispatchEvent(luaState, FacebookFragmentActivity.this.mListener, 0);
                        CoronaLua.deleteRef(luaState, FacebookFragmentActivity.this.mListener);
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void pushStringIfNotNull(LuaState luaState, String str, String str2) {
        if (str != null) {
            luaState.pushString(str);
            luaState.setField(-2, str2);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(CONTENT_VIEW_ID);
        setContentView(frameLayout, new ViewGroup.LayoutParams(-1, -1));
        String string = getIntent().getExtras().getString(FRAGMENT_NAME);
        this.mListener = getIntent().getExtras().getInt(FRAGMENT_LISTENER);
        Bundle bundleExtra = getIntent().getBundleExtra(FRAGMENT_EXTRAS);
        this.mFragment = null;
        if (string.equals("place")) {
            PlacePickerFragment placePickerFragment = new PlacePickerFragment();
            this.mFragment = placePickerFragment;
            String string2 = bundleExtra.getString("title");
            if (string2 != null) {
                placePickerFragment.setTitleText(string2);
            }
            String string3 = bundleExtra.getString("searchText");
            if (string3 != null) {
                placePickerFragment.setSearchText(string3);
            }
            String string4 = bundleExtra.getString("latitude");
            String string5 = bundleExtra.getString("longitude");
            if (!(string4 == null || string5 == null)) {
                Location location = new Location("passive");
                try {
                    location.setLatitude(Double.parseDouble(string4));
                    location.setLongitude(Double.parseDouble(string5));
                    placePickerFragment.setLocation(location);
                } catch (NumberFormatException e) {
                }
            }
            String string6 = bundleExtra.getString("resultsLimit");
            if (string6 != null) {
                try {
                    placePickerFragment.setResultsLimit(Double.valueOf(string6).intValue());
                } catch (NumberFormatException e2) {
                }
            }
            String string7 = bundleExtra.getString("radiusInMeters");
            if (string7 != null) {
                try {
                    placePickerFragment.setRadiusInMeters(Double.valueOf(string7).intValue());
                } catch (NumberFormatException e3) {
                }
            }
            placePickerFragment.setOnSelectionChangedListener(new PickerFragment.OnSelectionChangedListener() {
                public void onSelectionChanged(PickerFragment<?> pickerFragment) {
                    GraphPlace selection = ((PlacePickerFragment) pickerFragment).getSelection();
                    if (selection != null) {
                        FacebookFragmentActivity.this.pushPlaceSelection(selection);
                    }
                    ((InputMethodManager) FacebookFragmentActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(((ViewGroup) pickerFragment.getActivity().getWindow().getDecorView()).getApplicationWindowToken(), 0);
                    FacebookFragmentActivity.this.finish();
                }
            });
            placePickerFragment.setOnDoneButtonClickedListener(new PickerFragment.OnDoneButtonClickedListener() {
                public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
                    GraphPlace selection = ((PlacePickerFragment) pickerFragment).getSelection();
                    if (selection != null) {
                        FacebookFragmentActivity.this.pushPlaceSelection(selection);
                    }
                    ((InputMethodManager) FacebookFragmentActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(((ViewGroup) pickerFragment.getActivity().getWindow().getDecorView()).getApplicationWindowToken(), 0);
                    FacebookFragmentActivity.this.finish();
                }
            });
        } else if (string.equals(NativeProtocol.AUDIENCE_FRIENDS)) {
            this.mFragment = new FriendPickerFragment(new Bundle());
            this.mFragment.setOnDoneButtonClickedListener(new PickerFragment.OnDoneButtonClickedListener() {
                public void onDoneButtonClicked(PickerFragment<?> pickerFragment) {
                    FacebookFragmentActivity.this.pushFriendSelection(((FriendPickerFragment) pickerFragment).getSelection());
                    FacebookFragmentActivity.this.finish();
                }
            });
        }
        getSupportFragmentManager().beginTransaction().add((int) CONTENT_VIEW_ID, (Fragment) this.mFragment).commit();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        try {
            this.mFragment.loadData(false);
        } catch (Exception e) {
        }
    }
}
