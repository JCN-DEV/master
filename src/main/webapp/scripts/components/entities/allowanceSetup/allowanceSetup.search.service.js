'use strict';

angular.module('stepApp')
    .factory('AllowanceSetupSearch', function ($resource) {
        return $resource('api/_search/allowanceSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
