'use strict';

angular.module('stepApp')
    .factory('InstEmplTraining', function ($resource, DateUtils) {
        return $resource('api/instEmplTrainings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startedDate = DateUtils.convertLocaleDateFromServer(data.startedDate);
                    data.endedDate = DateUtils.convertLocaleDateFromServer(data.endedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.startedDate = DateUtils.convertLocaleDateToServer(data.startedDate);
                    data.endedDate = DateUtils.convertLocaleDateToServer(data.endedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.startedDate = DateUtils.convertLocaleDateToServer(data.startedDate);
                    data.endedDate = DateUtils.convertLocaleDateToServer(data.endedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
