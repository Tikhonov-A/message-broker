package ru.tikhonov.stats.statStep;

import ru.tikhonov.objects.Source;

import java.util.ArrayList;
import java.util.List;

public class SourcesState {
    List<SourceState> sourcesState = new ArrayList<>();


    public SourcesState(List<Source> sources) {
        for(Source s : sources) {
            if (s.isBusy()) {
                sourcesState.add(new SourceState(Integer.toString(s.getId()), "Generated"));
            }
            else {
                sourcesState.add(new SourceState(Integer.toString(s.getId()), "Waiting"));
            }
        }
    }

    public List<SourceState> getSourcesState() {
        return sourcesState;
    }
}
