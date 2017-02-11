'use strict';

describe('SisStudentInfoSubj Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSisStudentInfoSubj, MockSisStudentInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSisStudentInfoSubj = jasmine.createSpy('MockSisStudentInfoSubj');
        MockSisStudentInfo = jasmine.createSpy('MockSisStudentInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SisStudentInfoSubj': MockSisStudentInfoSubj,
            'SisStudentInfo': MockSisStudentInfo
        };
        createController = function() {
            $injector.get('$controller')("SisStudentInfoSubjDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:sisStudentInfoSubjUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
