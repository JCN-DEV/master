'use strict';

angular.module('stepApp')
    .factory('DlBookEditionSearch', function ($resource) {
        return $resource('api/_search/dlBookEditions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
