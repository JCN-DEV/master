'use strict';

angular.module('stepApp')
    .factory('InstMemShip', function ($resource, DateUtils) {
        return $resource('api/instMemShips/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dob = DateUtils.convertLocaleDateFromServer(data.dob);
                    data.date = DateUtils.convertLocaleDateFromServer(data.date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    data.date = DateUtils.convertLocaleDateToServer(data.date);
                    return angular.toJson(data);
                }
            }
        });
    })

    .factory('MemShipByEmail', function ($resource) {
        return $resource('api/instMemShips/instEmailUnique/:email', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });

