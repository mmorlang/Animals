package edu.cnm.deepdive.animals.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.animals.BuildConfig;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.service.AnimalService;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.List;


public class MainViewModel extends AndroidViewModel {

  private MutableLiveData<List<Animal>> animals;
  private MutableLiveData<Throwable> throwable;
  private AnimalService animalService;

  public MainViewModel(@NonNull Application application) {
    super(application);
    animalService = AnimalService.getInstance();
    animals = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    loadAnimals();
  }

  public LiveData<List<Animal>> getAnimals() {
    return animals;
  }
  public LiveData<Throwable> getThrowable(){
    return throwable;
  }

  private void loadAnimals() {

    animalService.getAnimals(BuildConfig.CLIENT_KEY)
        .subscribeOn(Schedulers.io())
        .subscribe(animals -> this.animals.postValue(animals),
            throwable -> this.throwable.postValue(throwable));

  }

}