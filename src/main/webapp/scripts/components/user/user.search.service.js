'use strict';

angular.module('stepApp')
    .factory('UsersSearch', function ($resource) {
        return $resource('api/_search/users/:query', {}, {
            'query': {
                method: 'GET',
                isArray: true
            }
        });
    });
