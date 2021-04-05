package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ultratude.com.staff.webservice.ResponseModels.SubRequestModels.*;

public class PortalClassStreamTeacherStaffAllocationsResponse {

        @SerializedName("getSchools")
        private List<PortalGetSchoolsResponse> getSchools;
        @SerializedName("getClasses")
        private List<PortalGetClassesResponse> getClasses;
        @SerializedName("getStreams")
        private List<PortalGetStreamResponse> getStreams;
        @SerializedName("getUnits")
        private List<PortalGetUnitsResponse> getUnits;
        @SerializedName("getStudySessions")
        private List<PortalGetStudySessionsResponse> getStudySessions;
        @SerializedName("getActivities")
        private List<PortalGetChargeTypeNamesResponse> getActivities;
        @SerializedName("getLessonsPeriods")
        private List<PortalGetLessonPeriods> getLessonsPeriods;
        @SerializedName("getStudentList")
        private List<PortalGetStudentList> getStudentList;
        @SerializedName("getRollCallSessionsList")
        private List<PortalRollCallSessionResponse> getRollCallSessionsList;

        @SerializedName("getTermsList")
        private List<PortalTermsResponse> getTermsResponse;
        @SerializedName("getBatchList")
        private List<PortalBatchsResponse> getBatchsResponse;


    public PortalClassStreamTeacherStaffAllocationsResponse(List<PortalGetSchoolsResponse> getSchools, List<PortalGetClassesResponse> getClasses, List<PortalGetStreamResponse> getStreams, List<PortalGetUnitsResponse> getUnits, List<PortalGetStudySessionsResponse> getStudySessions, List<PortalGetChargeTypeNamesResponse> getActivities, List<PortalGetLessonPeriods> getLessonsPeriods, List<PortalGetStudentList> getStudentList, List<PortalRollCallSessionResponse> getRollCallSessionsList,
                                                            List<PortalBatchsResponse> getBatchsResponse,
                                                            List<PortalTermsResponse> getTermsResponse) {
        this.getSchools = getSchools;
        this.getClasses = getClasses;
        this.getStreams = getStreams;
        this.getUnits = getUnits;
        this.getStudySessions = getStudySessions;
        this.getActivities = getActivities;
        this.getLessonsPeriods = getLessonsPeriods;
        this.getStudentList = getStudentList;
        this.getRollCallSessionsList = getRollCallSessionsList;

        this.getTermsResponse = getTermsResponse;
        this.getBatchsResponse = getBatchsResponse;
    }


    public List<PortalTermsResponse> getGetTermsResponse() {
        return getTermsResponse;
    }

    public List<PortalBatchsResponse> getGetBatchsResponse() {
        return getBatchsResponse;
    }

    public List<PortalGetSchoolsResponse> getGetSchools() {
        return getSchools;
    }

    public List<PortalGetClassesResponse> getGetClasses() {
        return getClasses;
    }

    public List<PortalGetStreamResponse> getGetStreams() {
        return getStreams;
    }

    public List<PortalGetUnitsResponse> getGetUnits() {
        return getUnits;
    }

    public List<PortalGetStudySessionsResponse> getGetStudySessions() {
        return getStudySessions;
    }

    public List<PortalGetChargeTypeNamesResponse> getGetActivities() {
        return getActivities;
    }

    public List<PortalGetLessonPeriods> getGetLessonsPeriods() {
        return getLessonsPeriods;
    }

    public List<PortalGetStudentList> getGetStudentList() {
        return getStudentList;
    }

    public List<PortalRollCallSessionResponse> getGetRollCallSessionsList() {
        return getRollCallSessionsList;
    }
}
