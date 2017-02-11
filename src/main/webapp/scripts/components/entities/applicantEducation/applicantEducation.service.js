'use strict';

angular.module('stepApp')
    .factory('ApplicantEducation', function ($resource, DateUtils) {
        return $resource('api/applicantEducations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.examYear = DateUtils.convertLocaleDateFromServer(data.examYear);
                    data.examResultDate = DateUtils.convertLocaleDateFromServer(data.examResultDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.examYear = DateUtils.convertLocaleDateToServer(data.examYear);
                    data.examResultDate = DateUtils.convertLocaleDateToServer(data.examResultDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.examYear = DateUtils.convertLocaleDateToServer(data.examYear);
                    data.examResultDate = DateUtils.convertLocaleDateToServer(data.examResultDate);
                    return angular.toJson(data);
                }
            }
        });
    });