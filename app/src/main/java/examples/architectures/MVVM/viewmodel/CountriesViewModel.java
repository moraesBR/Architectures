package examples.architectures.MVVM.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import examples.architectures.MVVM.model.Country;
import examples.architectures.MVVM.model.CountryService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesViewModel extends ViewModel {
    private final MutableLiveData<List<String>> countries = new MutableLiveData<>();
    private final MutableLiveData<Boolean> countryError = new MutableLiveData<>();

    private CountryService service;

    public CountriesViewModel() {
        service = new CountryService();
        fetchCountries();
    }

    public LiveData<List<String>> getCountries(){
        return countries;
    }

    public LiveData<Boolean> getCountryError(){
        return countryError;
    }

    private void fetchCountries() {
        service.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(List<Country> values) {
                        List<String> countryNames = new ArrayList<>();
                        for (Country country : values){
                            countryNames.add(country.countryName);
                        }
                        countries.setValue(countryNames);
                        countryError.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        countryError.setValue(true);
                    }
                });
    }

    public void onRefresh(){
        fetchCountries();
    }
}
