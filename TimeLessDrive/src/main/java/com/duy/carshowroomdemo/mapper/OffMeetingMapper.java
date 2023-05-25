package com.duy.carshowroomdemo.mapper;

import com.duy.carshowroomdemo.dto.OffMeetingDto;
import com.duy.carshowroomdemo.entity.OffMeeting;

public class OffMeetingMapper implements Mapper<OffMeeting, OffMeetingDto>  {
    @Override
    public OffMeeting toEntity(OffMeetingDto source) {
        return (source == null) ? null : modelMapper.map(source, OffMeeting.class);
    }

    @Override
    public OffMeetingDto toDto(OffMeeting source) {
        return (source == null) ? null : modelMapper.map(source, OffMeetingDto.class);
    }
}
