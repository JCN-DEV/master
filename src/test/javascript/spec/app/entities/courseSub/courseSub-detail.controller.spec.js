'use strict';

describe('CourseSub Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCourseSub, MockCourseTech;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCourseSub = jasmine.createSpy('MockCourseSub');
        MockCourseTech = jasmine.createSpy('MockCourseTech');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CourseSub': MockCourseSub,
            'CourseTech': MockCourseTech
        };
        createController = function() {
            $injector.get('$controller')("CourseSubDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:courseSubUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
