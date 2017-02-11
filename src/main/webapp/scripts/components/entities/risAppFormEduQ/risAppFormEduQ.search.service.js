'use strict';

angular.module('stepApp')
    .factory('RisAppFormEduQSearch', function ($resource) {
        return $resource('api/_search/risAppFormEduQs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
