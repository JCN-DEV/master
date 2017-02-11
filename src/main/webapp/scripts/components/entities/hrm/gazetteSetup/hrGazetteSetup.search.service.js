'use strict';

angular.module('stepApp')
    .factory('HrGazetteSetupSearch', function ($resource) {
        return $resource('api/_search/hrGazetteSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
