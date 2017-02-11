'use strict';

angular.module('stepApp')
    .factory('SmsServiceForward', function ($resource, DateUtils) {
        return $resource('api/smsServiceForwards/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.forwardDate = DateUtils.convertLocaleDateFromServer(data.forwardDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.forwardDate = DateUtils.convertLocaleDateToServer(data.forwardDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.forwardDate = DateUtils.convertLocaleDateToServer(data.forwardDate);
                    return angular.toJson(data);
                }
            }
        });
    });
