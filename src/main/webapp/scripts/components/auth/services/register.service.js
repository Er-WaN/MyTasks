'use strict';

angular.module('myTasksApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


