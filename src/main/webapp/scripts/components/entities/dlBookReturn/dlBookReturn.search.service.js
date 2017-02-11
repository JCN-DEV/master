'use strict';

angular.module('stepApp')
    .factory('DlBookReturnSearch', function ($resource) {
        return $resource('api/_search/dlBookReturns/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
