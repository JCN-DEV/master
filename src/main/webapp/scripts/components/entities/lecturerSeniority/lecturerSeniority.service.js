'use strict';

angular.module('stepApp')
    .factory('LecturerSeniority', function ($resource, DateUtils) {
        return $resource('api/lecturerSenioritys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.firstMPOEnlistingDate = DateUtils.convertLocaleDateFromServer(data.firstMPOEnlistingDate);
                    data.joiningDateAsLecturer = DateUtils.convertLocaleDateFromServer(data.joiningDateAsLecturer);
                    data.dob = DateUtils.convertLocaleDateFromServer(data.dob);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.firstMPOEnlistingDate = DateUtils.convertLocaleDateToServer(data.firstMPOEnlistingDate);
                    data.joiningDateAsLecturer = DateUtils.convertLocaleDateToServer(data.joiningDateAsLecturer);
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.firstMPOEnlistingDate = DateUtils.convertLocaleDateToServer(data.firstMPOEnlistingDate);
                    data.joiningDateAsLecturer = DateUtils.convertLocaleDateToServer(data.joiningDateAsLecturer);
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    return angular.toJson(data);
                }
            }
        });
    });