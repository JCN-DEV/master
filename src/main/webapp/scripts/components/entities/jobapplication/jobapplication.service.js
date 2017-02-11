'use strict';

angular.module('stepApp')
    .factory('Jobapplication', function ($resource, DateUtils) {
        return $resource('api/jobapplications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.appliedDate = DateUtils.convertLocaleDateFromServer(data.appliedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    if(data){
                        data.appliedDate = DateUtils.convertLocaleDateToServer(data.appliedDate);
                        return angular.toJson(data);
                    }

                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.appliedDate = DateUtils.convertLocaleDateToServer(data.appliedDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('JobapplicationCvSort', function ($resource, DateUtils) {
        return $resource('api/jobapplications/cvSorting', {}, {
            'save': {
                method: 'POST',
                isArray: true
            }
        });
    }).factory('JobApplicationsByJob', function ($resource) {
        return $resource('api/jobapplications/jobs/:id', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
