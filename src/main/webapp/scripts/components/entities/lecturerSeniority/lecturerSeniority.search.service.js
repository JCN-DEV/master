'use strict';

angular.module('stepApp')
    .factory('LecturerSenioritySearch', function ($resource) {
        return $resource('api/_search/lecturerSenioritys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
