'use strict';

angular.module('stepApp')
    .factory('StaffCountSearch', function ($resource) {
        return $resource('api/_search/staffCounts/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
