'use strict';

describe('LecturerSeniority Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockLecturerSeniority, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockLecturerSeniority = jasmine.createSpy('MockLecturerSeniority');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'LecturerSeniority': MockLecturerSeniority,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("LecturerSeniorityDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:lecturerSeniorityUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
