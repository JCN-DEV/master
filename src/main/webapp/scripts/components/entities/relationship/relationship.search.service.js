'use strict';

angular.module('stepApp')
    .factory('RelationshipSearch', function ($resource) {
        return $resource('api/_search/relationships/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
