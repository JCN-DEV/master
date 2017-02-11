'use strict';

angular.module('stepApp')
    .factory('Employee', function ($resource, DateUtils) {
        return $resource('api/employees/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if(data){
                        data = angular.fromJson(data);
                        data.dob = DateUtils.convertLocaleDateFromServer(data.dob);
                        data.gbResolutionReceiveDate = DateUtils.convertLocaleDateFromServer(data.gbResolutionReceiveDate);
                        data.circularPublishedDate = DateUtils.convertLocaleDateFromServer(data.circularPublishedDate);
                        data.recruitExamReceiveDate = DateUtils.convertLocaleDateFromServer(data.recruitExamReceiveDate);
                        data.recruitApproveGBResolutionDate = DateUtils.convertLocaleDateFromServer(data.recruitApproveGBResolutionDate);
                        data.recruitmentDate = DateUtils.convertLocaleDateFromServer(data.recruitmentDate);
                        data.presentInstituteJoinDate = DateUtils.convertLocaleDateFromServer(data.presentInstituteJoinDate);
                        data.presentPostJoinDate = DateUtils.convertLocaleDateFromServer(data.presentPostJoinDate);
                        data.timeScaleGBResolutionDate = DateUtils.convertLocaleDateFromServer(data.timeScaleGBResolutionDate);

                    }
                   return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    data.gbResolutionReceiveDate = DateUtils.convertLocaleDateToServer(data.gbResolutionReceiveDate);
                    data.circularPublishedDate = DateUtils.convertLocaleDateToServer(data.circularPublishedDate);
                    data.recruitExamReceiveDate = DateUtils.convertLocaleDateToServer(data.recruitExamReceiveDate);
                    data.recruitApproveGBResolutionDate = DateUtils.convertLocaleDateToServer(data.recruitApproveGBResolutionDate);
                    data.recruitmentDate = DateUtils.convertLocaleDateToServer(data.recruitmentDate);
                    data.presentInstituteJoinDate = DateUtils.convertLocaleDateToServer(data.presentInstituteJoinDate);
                    data.presentPostJoinDate = DateUtils.convertLocaleDateToServer(data.presentPostJoinDate);
                    data.timeScaleGBResolutionDate = DateUtils.convertLocaleDateToServer(data.timeScaleGBResolutionDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    data.gbResolutionReceiveDate = DateUtils.convertLocaleDateToServer(data.gbResolutionReceiveDate);
                    data.circularPublishedDate = DateUtils.convertLocaleDateToServer(data.circularPublishedDate);
                    data.recruitExamReceiveDate = DateUtils.convertLocaleDateToServer(data.recruitExamReceiveDate);
                    data.recruitApproveGBResolutionDate = DateUtils.convertLocaleDateToServer(data.recruitApproveGBResolutionDate);
                    data.recruitmentDate = DateUtils.convertLocaleDateToServer(data.recruitmentDate);
                    data.presentInstituteJoinDate = DateUtils.convertLocaleDateToServer(data.presentInstituteJoinDate);
                    data.presentPostJoinDate = DateUtils.convertLocaleDateToServer(data.presentPostJoinDate);
                    data.timeScaleGBResolutionDate = DateUtils.convertLocaleDateToServer(data.timeScaleGBResolutionDate);
                    return angular.toJson(data);
                }
            }
        });
    });
