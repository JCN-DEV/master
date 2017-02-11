'use strict';

angular.module('stepApp')
    .factory('UmracRightsLogSearch', function ($resource) {
        return $resource('api/_search/umracRightsLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
