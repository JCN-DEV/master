'use strict';

angular.module('stepApp')
    .factory('InstEmplPayscaleHist', function ($resource, DateUtils) {
        return $resource('api/instEmplPayscaleHists/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.activationDate = DateUtils.convertLocaleDateFromServer(data.activationDate);
                    data.endDate = DateUtils.convertLocaleDateFromServer(data.endDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.activationDate = DateUtils.convertLocaleDateToServer(data.activationDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.activationDate = DateUtils.convertLocaleDateToServer(data.activationDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    return angular.toJson(data);
                }
            }
        });
    });
