'use strict';

angular.module('stepApp')
    .factory('ModuleSearch', function ($resource) {
        return $resource('api/_search/modules/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
