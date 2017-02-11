'use strict';

angular.module('stepApp')
    .factory('PfmsUtmostGpfAppSearch', function ($resource) {
        return $resource('api/_search/pfmsUtmostGpfApps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
