'use strict';

angular.module('stepApp')
    .factory('ReligionSearch', function ($resource) {
        return $resource('api/_search/religions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
