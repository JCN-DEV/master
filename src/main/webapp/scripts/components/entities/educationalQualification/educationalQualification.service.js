'use strict';

angular.module('stepApp')
    .factory('EducationalQualification', function ($resource, DateUtils) {
        return $resource('api/educationalQualifications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.passingYear = DateUtils.convertLocaleDateFromServer(data.passingYear);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.passingYear = DateUtils.convertLocaleDateToServer(data.passingYear);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.passingYear = DateUtils.convertLocaleDateToServer(data.passingYear);
                    return angular.toJson(data);
                }
            }
        });
    });