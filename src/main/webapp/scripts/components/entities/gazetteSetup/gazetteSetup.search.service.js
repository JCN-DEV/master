'use strict';

angular.module('stepApp')
    .factory('GazetteSetupSearch', function ($resource) {
        return $resource('api/_search/gazetteSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
