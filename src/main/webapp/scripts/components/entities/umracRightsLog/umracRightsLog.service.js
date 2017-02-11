'use strict';

angular.module('stepApp')
    .factory('UmracRightsLog', function ($resource, DateUtils) {
        return $resource('api/umracRightsLogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.changeDate = DateUtils.convertLocaleDateFromServer(data.changeDate);
                    data.updatedTime = DateUtils.convertLocaleDateFromServer(data.updatedTime);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.changeDate = DateUtils.convertLocaleDateToServer(data.changeDate);
                    data.updatedTime = DateUtils.convertLocaleDateToServer(data.updatedTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.changeDate = DateUtils.convertLocaleDateToServer(data.changeDate);
                    data.updatedTime = DateUtils.convertLocaleDateToServer(data.updatedTime);
                    return angular.toJson(data);
                }
            }
        });
    });
