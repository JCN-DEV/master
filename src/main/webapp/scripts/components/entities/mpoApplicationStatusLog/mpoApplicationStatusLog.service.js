'use strict';

angular.module('stepApp')
    .factory('MpoApplicationStatusLog', function ($resource, DateUtils) {
        return $resource('api/mpoApplicationStatusLogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fromDate = DateUtils.convertLocaleDateFromServer(data.fromDate);
                    data.toDate = DateUtils.convertLocaleDateFromServer(data.toDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.fromDate = DateUtils.convertLocaleDateToServer(data.fromDate);
                    data.toDate = DateUtils.convertLocaleDateToServer(data.toDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.fromDate = DateUtils.convertLocaleDateToServer(data.fromDate);
                    data.toDate = DateUtils.convertLocaleDateToServer(data.toDate);
                    return angular.toJson(data);
                }
            }
        });
    });
