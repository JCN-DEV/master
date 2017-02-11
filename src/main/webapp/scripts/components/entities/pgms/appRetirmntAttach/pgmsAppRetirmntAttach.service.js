'use strict';

angular.module('stepApp')
    .factory('PgmsAppRetirmntAttach', function ($resource, DateUtils) {
        return $resource('api/pgmsAppRetirmntAttachs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
