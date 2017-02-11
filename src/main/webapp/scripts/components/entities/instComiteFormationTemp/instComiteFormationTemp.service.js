'use strict';

angular.module('stepApp')
    .factory('InstComiteFormationTemp', function ($resource, DateUtils) {
        return $resource('api/instComiteFormationTemps/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.formationDate = DateUtils.convertLocaleDateFromServer(data.formationDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.formationDate = DateUtils.convertLocaleDateToServer(data.formationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.formationDate = DateUtils.convertLocaleDateToServer(data.formationDate);
                    return angular.toJson(data);
                }
            }
        });
    });
