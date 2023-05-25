package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.FeedbackDto;
import com.duy.carshowroomdemo.entity.Feedback;

public class FeedbackMapper implements Mapper<Feedback, FeedbackDto> {
    @Override
    public Feedback toEntity(FeedbackDto source) {
        return (source == null) ? null : modelMapper.map(source, Feedback.class);
    }

    @Override
    public FeedbackDto toDto(Feedback source) {
        return (source == null) ? null : modelMapper.map(source, FeedbackDto.class);
    }
}
