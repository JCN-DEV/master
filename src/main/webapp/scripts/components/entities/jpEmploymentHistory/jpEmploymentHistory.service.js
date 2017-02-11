'use strict';

angular.module('stepApp')
    .factory('JpEmploymentHistory', function ($resource, DateUtils) {
        return $resource('api/jpEmploymentHistorys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startFrom = DateUtils.convertLocaleDateFromServer(data.startFrom);
                    data.endTo = DateUtils.convertLocaleDateFromServer(data.endTo);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startFrom = DateUtils.convertLocaleDateToServer(data.startFrom);
                    data.endTo = DateUtils.convertLocaleDateToServer(data.endTo);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startFrom = DateUtils.convertLocaleDateToServer(data.startFrom);
                    data.endTo = DateUtils.convertLocaleDateToServer(data.endTo);
                    return angular.toJson(data);
                }
            }
        });
    });
