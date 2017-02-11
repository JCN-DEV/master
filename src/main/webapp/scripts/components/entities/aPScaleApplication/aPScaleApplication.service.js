'use strict';

angular.module('stepApp')
    .factory('APScaleApplication', function ($resource, DateUtils) {
        return $resource('api/aPScaleApplications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date = DateUtils.convertLocaleDateFromServer(data.date);
                    data.resulationDate = DateUtils.convertLocaleDateFromServer(data.resulationDate);
                    data.workingBreakStart = DateUtils.convertLocaleDateFromServer(data.workingBreakStart);
                    data.workingBreakEnd = DateUtils.convertLocaleDateFromServer(data.workingBreakEnd);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocaleDateToServer(data.date);
                    data.resulationDate = DateUtils.convertLocaleDateToServer(data.resulationDate);
                    data.workingBreakStart = DateUtils.convertLocaleDateToServer(data.workingBreakStart);
                    data.workingBreakEnd = DateUtils.convertLocaleDateToServer(data.workingBreakEnd);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date = DateUtils.convertLocaleDateToServer(data.date);
                    data.resulationDate = DateUtils.convertLocaleDateToServer(data.resulationDate);
                    data.workingBreakStart = DateUtils.convertLocaleDateToServer(data.workingBreakStart);
                    data.workingBreakEnd = DateUtils.convertLocaleDateToServer(data.workingBreakEnd);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('ForwardApApplication', function ($resource, DateUtils) {
        return $resource('api/aPScaleApplications/forward/:forwardTo', {}, {
        'forward': {
            method: 'PUT',
            transformRequest: function (data) {
                data.date = DateUtils.convertLocaleDateToServer(data.date);
                return angular.toJson(data);
            }
        }

    });
});
