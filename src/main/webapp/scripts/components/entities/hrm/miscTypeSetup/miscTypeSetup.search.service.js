'use strict';

angular.module('stepApp')
    .factory('MiscTypeSetupSearch', function ($resource) {
        return $resource('api/_search/miscTypeSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
