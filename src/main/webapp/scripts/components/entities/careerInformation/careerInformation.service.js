'use strict';

angular.module('stepApp')
    .factory('CareerInformation', function ($resource, DateUtils) {
        return $resource('api/careerInformations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.availableFrom = DateUtils.convertLocaleDateFromServer(data.availableFrom);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.availableFrom = DateUtils.convertLocaleDateToServer(data.availableFrom);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.availableFrom = DateUtils.convertLocaleDateToServer(data.availableFrom);
                    return angular.toJson(data);
                }
            }
        });
    });