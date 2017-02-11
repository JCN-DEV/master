'use strict';

angular.module('stepApp')
    .factory('InstituteLandSearch', function ($resource) {
        return $resource('api/_search/instituteLands/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
